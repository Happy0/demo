package demo.utils;

import demo.model.Player;

import java.util.Iterator;
import java.util.List;

public class SlidingWindowIterator<E> implements Iterator<List<E>> {

    private final int windowSize;
    private final List<E> backingList;

    private int currentItem = 0;
    private int listSize = 0;
    private int itemsLeft = 0;

    private int currentPosition = 0;

    public SlidingWindowIterator(List<E> backingList, int windowSize)
    {
        this.backingList = backingList;
        this.windowSize = windowSize;

        this.listSize = backingList.size();
        this.itemsLeft = listSize;
    }

    @Override
    public boolean hasNext() {
        return itemsLeft != 0;
    }

    @Override
    public List<E> next() {
        if (itemsLeft >= windowSize)
        {

            // we always step one... This could be made more generic to accept a step parameter
            List<E> retList = backingList.subList(currentItem, currentItem + windowSize);
            currentItem = currentItem + 1;
            itemsLeft = itemsLeft - 1;

            return retList;
        }
        else
        {
            List<E> retList = backingList.subList(currentItem, currentItem + itemsLeft);
            itemsLeft = 0;
            return retList;
        }
    }
}
