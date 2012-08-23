package com.facebook.presto;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractSequentialIterator;

import java.util.Iterator;

public class Range
    implements Iterable<Long>
{
    private final long start;
    private final long end;

    public Range(long start, long end)
    {
        Preconditions.checkArgument(start <= end, "start (%s) must be <= end (%s)", start, end);

        this.start = start;
        this.end = end;
    }

    /**
     * Create a range from start (inclusive) to end (inclusive)
     */
    public static Range create(long start, long end)
    {
        return new Range(start, end);
    }

    public long getStart()
    {
        return start;
    }

    public long getEnd()
    {
        return end;
    }

    public long length()
    {
        return end - start + 1;
    }

    public boolean contains(long value)
    {
        return value >= start && value <= end;
    }

    public boolean overlaps(Range other)
    {
        return start <= other.end && other.start <= end;
    }

    public Range intersect(Range other)
    {
        Preconditions.checkArgument(overlaps(other), "Ranges do not overlap %s vs %s", this, other);

        return create(Math.max(start, other.start), Math.min(end, other.end));
    }

    public String toString()
    {
        return String.format("[%s..%s]", start, end);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Range range = (Range) o;

        if (end != range.end) {
            return false;
        }
        if (start != range.start) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) (start ^ (start >>> 32));
        result = 31 * result + (int) (end ^ (end >>> 32));
        return result;
    }

    @Override
    public Iterator<Long> iterator()
    {
        return new AbstractSequentialIterator<Long>(start)
        {
            @Override
            protected Long computeNext(Long previous)
            {
                if (previous == end) {
                    return null;
                }

                return previous + 1;
            }
        };
    }
}
