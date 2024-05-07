package com.qhx.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-18 15:02
 **/

public class CommonUtil
{
    /**
     *   把list1对比list2不同的元素返回
     *
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static  <T> List<T> findList1List2(List<T> list1, List<T> list2){
        List<T> resList = new ArrayList<>();
        int l = list2.size();
        loop : for (T item : list1)
        {

            for (int i = 0,j = l - 1 ; i < j; i++,j--)
            {
                if(StringUtil.equals(item.toString(),list2.get(i).toString()))
                {
                    continue loop;
                }
                if(StringUtil.equals(item.toString(),list2.get(j).toString()))
                {
                    continue  loop;
                }

            }
            resList.add(item);
        }
        return resList;
    }

}
