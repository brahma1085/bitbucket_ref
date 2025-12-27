package generics;

public class PairCollection<T,U> implements Iterable<T>
{
    // code assumes random access so force implementation class
	  private final ArrayList<T> m_tValues;
    private final ArrayList<U> m_uValues;
    
    public PairCollection() {
        m_tValues = new ArrayList<T>();
        m_uValues = new ArrayList<U>();
    }
    
    public void add(T t, U u) {
        m_tValues.add(t);
        m_uValues.add(u);
    }
    
    public void clear() {
        m_tValues.clear();
        m_uValues.clear();
    }

    public PairIterator iterator() {
        return new PairIterator();
    }
    
    public class PairIterator implements Iterator<T>
    {
        private int m_offset;

        public boolean hasNext() {
            return m_offset < m_tValues.size();
        }

        public T next() {
            if (m_offset < m_tValues.size()) {
                return m_tValues.get(m_offset++);
            } else {
                throw new NoSuchElementException();
            }
        }

        public U matching() {
            if (m_offset > 0) {
                return m_uValues.get(m_offset-1);
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
