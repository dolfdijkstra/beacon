package com.dolfdijkstra.beacon.test;

import java.util.Arrays;

import com.dolfdijkstra.beacon.test.OptionsMatrix.Row;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class OptionsMatrixTest {

    @Test
    public void testMatrix() {
        OptionsMatrix<Integer> matrix = new OptionsMatrix<Integer>(4, Integer[].class);

        Row<Integer>[] table = matrix.addVariant("a", 0, 10, 200).addVariant("b", 0, 200).addVariant("c", 10, 150, 500)
                .addVariant("d", 0, 200).createTable();

        System.out.println(Arrays.toString(matrix.getVariantNames()));

        for (Row<Integer> row : table) {

            for (int j = 0; j < row.size(); j++) {
                System.out.print(StringUtils.leftPad(Integer.toString(row.get(j)), 4));
            }
            System.out.println();
        }
    }
}
