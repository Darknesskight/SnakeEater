package org.SnakeEater.util;

import java.util.Comparator;

import org.SnakeEater.attributes.Renderable;

public class RenderableComparator implements Comparator<Renderable> {

    public int compare(Renderable r1, Renderable r2) {
        return (r1.getRenderPriority() > r2.getRenderPriority() ? -1 : (r1.getRenderPriority() == r2.getRenderPriority() ? 0 : 1));
    }

}
