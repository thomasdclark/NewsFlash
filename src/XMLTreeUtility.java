import components.xmltree.XMLTree;

/**
 * A utility class for XMLTree.
 * 
 * @author Thomas Clark
 * 
 */
public final class XMLTreeUtility {

    /**
     * Private constructor so this class is not instantiated
     */
    private XMLTreeUtility(String source) {
    }

    /**
     * Finds the first occurrence of the given tag among the children and
     * returns its index; returns -1 if not found.
     */
    public static int getChildElement(XMLTree tree, String tag) {
        /*
         * Go through all of the children of the given root and check if their
         * label is equal to the tag. If a child is found where this is true,
         * the index of that child is returned. Else, -1 is returned.
         */
        int index = -1;
        int childNumber = tree.numberOfChildren();
        int i = 0;
        while ((index < 0) && (i < childNumber)) {
            XMLTree child = tree.child(i);
            if (child.label().equals(tag)) {
                index = i;
            }
            i++;
        }
        return index;
    }

}