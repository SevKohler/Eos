package org.bih.eos.converter;

import com.nedap.archie.rm.archetyped.Locatable;
import org.bih.eos.exceptions.UnprocessableEntityException;

import java.util.List;
import java.util.Optional;

public class PathProcessor {

    public static Optional<?> getItemAtPath(Locatable contentItem, String path) {
        Object item = getSingleItem(contentItem, path);
        if (item != null) {
            return Optional.of(item);
        } else {
            return Optional.empty();
        }
    }

    private static Object getSingleItem(Locatable contentItem, String path) {
        if (path.equals(".")) {
            return contentItem;
        } else if (path.startsWith("../")) {
            path = path.substring(3);
            if (contentItem.getParent() != null) {
                Locatable parent = (Locatable) contentItem.getParent();
                if (path.length() != 0) {
                    return getSingleItem(parent, path);
                }
                return parent;
            }else{
                return null;
            }
        }
        return contentItem.itemAtPath(path);
    }

    public static List<Object> getMultipleItems(Locatable contentItem, String path) {
        if (path.equals(".")) {
            throw new UnprocessableEntityException(". is not supported for getItemsAtPath");
        } else if (path.startsWith("../")) {
            path = path.substring(3);
            if (contentItem.getParent() != null) {
                Locatable parent = (Locatable) contentItem.getParent();
                if (path.length() != 0) {
                    return getMultipleItems(parent, path);
                }
                return List.of(parent);//TODO other solution, when parent parents is called and its only e.g. a point event and not a list what to do ?
            }else{
                return null;
            }
        }
        return contentItem.itemsAtPath(path);
    }

}
