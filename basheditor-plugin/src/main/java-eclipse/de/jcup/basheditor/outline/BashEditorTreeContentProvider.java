/*
 * Copyright 2017 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.basheditor.outline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;

import de.jcup.basheditor.SimpleStringUtils;
import de.jcup.basheditor.script.BashFunction;
import de.jcup.basheditor.script.BashScriptModel;
import de.jcup.basheditor.script.BashVariable;
import de.jcup.basheditor.script.BashVariableAssignment;
import de.jcup.basheditor.script.parser.ParseToken;

public class BashEditorTreeContentProvider implements ITreeContentProvider {

    private static final String BASH_SCRIPT_CONTAINS_ERRORS = "Bash script contains errors.";
    private static final String BASH_SCRIPT_DOES_NOT_CONTAIN_ANY_FUNCTIONS = "Bash script does not contain any functions";
    private static final Object[] RESULT_WHEN_EMPTY = new Object[] { BASH_SCRIPT_DOES_NOT_CONTAIN_ANY_FUNCTIONS };
    private Object[] items;
    private Object monitor = new Object();

    BashEditorTreeContentProvider() {
        items = RESULT_WHEN_EMPTY;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        synchronized (monitor) {
            if (inputElement != null && !(inputElement instanceof BashScriptModel)) {
                return new Object[] { "Unsupported input element:" + inputElement };
            }
            if (items != null && items.length > 0) {
                return items;
            }
        }
        return RESULT_WHEN_EMPTY;
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Item) {
            Item item = (Item) parentElement;
            if (!item.hasChildren()) {
                return null;
            }
            return item.getChildren().toArray();
        }
        return null;
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof Item) {
            Item item = (Item) element;
            return item.hasChildren();
        }
        return false;
    }

    private Item[] createItems(BashScriptModel model) {
        List<Item> list = new ArrayList<>();
        addVariables(list, model.getVariables());
        for (BashFunction function : model.getFunctions()) {
            Item item = new Item();
            item.name = function.getName();
            item.type = ItemType.FUNCTION;
            item.offset = function.getPosition();
            item.length = function.getLengthToNameEnd();
            item.endOffset = function.getEnd();
            list.add(item);

            if (!function.getVariables().isEmpty()) {
                addVariables(item.getChildren(), function.getVariables());
            }

        }
        if (list.isEmpty()) {
            Item item = new Item();
            item.name = BASH_SCRIPT_DOES_NOT_CONTAIN_ANY_FUNCTIONS;
            item.type = ItemType.META_INFO;
            item.offset = 0;
            item.length = 0;
            item.endOffset = 0;
            list.add(item);
        }
        /* debug part */
        if (model.hasDebugTokens()) {
            if (model.hasErrors()) {
                Item item = new Item();
                item.name = BASH_SCRIPT_CONTAINS_ERRORS;
                item.type = ItemType.META_ERROR;
                item.offset = 0;
                item.length = 0;
                item.endOffset = 0;
                list.add(0, item);
            }
            for (ParseToken token : model.getDebugTokens()) {
                Item item = new Item();
                item.name = SimpleStringUtils.shortString(token.getText(), 40) + " :<- " + token.createTypeDescription();
                item.type = ItemType.META_DEBUG;
                item.offset = token.getStart();
                item.length = token.getText().length();
                item.endOffset = token.getEnd();
                list.add(item);
            }
        }
        return list.toArray(new Item[list.size()]);

    }

    private void addVariables(List<Item> list, Map<String, BashVariable> variablesMap) {
        for (BashVariable variable : variablesMap.values()) {
            BashVariableAssignment initial = variable.getInitialAssignment();
            Item item = new Item();
            item.name = variable.getName();
            item.type = ItemType.GLOBAL_VARIABLE;
            item.offset = initial.getStart();
            item.length = initial.getEnd() - initial.getStart();
            item.endOffset = initial.getEnd();
            list.add(item);
        }
    }

    public void rebuildTree(BashScriptModel model) {
        synchronized (monitor) {
            if (model == null) {
                items = null;
                return;
            }
            items = createItems(model);
        }
    }

    public List<Item> findItemsOfType(ItemType... types) {
        List<Item> result = new ArrayList<>();

        if (items != null) {
            for (Object obj : items) {
                if (!(obj instanceof Item)) {
                    continue;
                }
                Item item = (Item) obj;
                for (ItemType type : types) {
                    if (type.equals(item.type)) {
                        result.add(item);
                        break;
                    }
                }
            }
        }

        return result;
    }

    public Item tryToFindByOffset(int offset) {
        synchronized (monitor) {
            return findItemByOffset(offset, items);
        }
    }

    private Item findItemByOffset(int offset, Object[] items) {
        if (items == null) {
            return null;
        }
        for (Object oitem : items) {
            if (!(oitem instanceof Item)) {
                continue;
            }
            Item item = (Item) oitem;
            int itemStart = item.getOffset();
            int itemEnd = item.getEndOffset();

            /* do children first - otherwise parent can hide children... */
            if (item.hasChildren()) {
                Item itemFound = findItemByOffset(offset, item.getChildren().toArray());
                if (itemFound != null) {
                    return itemFound;
                }
            }
            /* do parent now */
            if (offset >= itemStart && offset <= itemEnd) {
                return item;
            }
        }
        return null;
    }

}
