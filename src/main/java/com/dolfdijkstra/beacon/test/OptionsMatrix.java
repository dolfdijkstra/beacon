package com.dolfdijkstra.beacon.test;

import java.lang.reflect.Array;

public class OptionsMatrix<E> {
    private int count = 0;
    private final Class<E[]> type;
    private OptionList<E>[] variants;

    /**
     * 
     */
    
    @SuppressWarnings("unchecked")
    public OptionsMatrix(int size, Class<E[]> type) {
        super();
        this.type = type;
        variants = new OptionList[size];
    }

    static class OptionList<T> {

        private final String name;
        private final T[] options;

        /**
         * @param name
         * @param options
         */
        public OptionList(String name, T... options) {
            super();
            this.name = name;
            this.options = options;
        }

        static <T> OptionList<T> make(String name, T... options) {
            return new OptionList<T>(name, options);
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the options
         */
        public T[] getOptions() {
            return options;
        }

        public int size() {
            return options.length;
        }

        public T get(int i) {
            return options[i];
        }
    }

    static class Row<E> {
        private E[] columns;

        public Row(int size, Class<E[]> type) {
            Object o = Array.newInstance(type.getComponentType(), size);
            columns =  type.cast(o);
        }

        public int size() {
            return columns.length;
        }

        public E get(int j) {
            return columns[j];
        }
    }

    Row<E> createRow(int num) {
        // 0 -> print variants[0][0] ,variants[1][0], variants[2][0]
        // 1 -> print variants[0][1] ,variants[1][0], variants[2][0]
        // 2 -> print variants[0][2] ,variants[1][0], variants[2][0]
        // 3 -> print variants[0][0] ,variants[1][1], variants[2][0]
        // 4 -> print variants[0][1] ,variants[1][1], variants[2][0]
        // 5 -> print variants[0][2] ,variants[1][1], variants[2][0]

        int next = 1;
        
        Row<E> row = new Row<E>(variants.length, type);
        for (int i = 0; i < variants.length; i++) {
            int val = (num / next) % variants[i].size();
            row.columns[i] = variants[i].get(val);
            next *= variants[i].size();
        }
        return row;

    }

    public OptionsMatrix<E> addVariant(String name, E... options) {
        variants[count++] = OptionList.make(name, options);
        return this;

    }

    public Row<E>[] createTable() {
        int num = rows();

        @SuppressWarnings("unchecked")
        Row<E>[] table = new Row[num];

        for (int i = 0; i < num; i++) {
            table[i] = createRow(i);
        }
        return table;
    }

    private int rows() {
        int num = 1;
        for (int i = 0; i < variants.length; i++) {
            num *= variants[i].size();
        }
        return num;
    }

    public String[] getVariantNames() {
        String[] x = new String[variants.length];
        for (int i = 0; i < variants.length; i++) {
            x[i] = variants[i].name;
        }
        return x;
    }

}
