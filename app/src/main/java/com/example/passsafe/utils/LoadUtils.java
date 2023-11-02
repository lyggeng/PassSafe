package com.example.passsafe.utils;

import com.example.passsafe.MyApplication;
import com.example.passsafe.beans.Item;
import com.example.passsafe.greendao.ItemDao;

import java.util.Arrays;
import java.util.List;

public class LoadUtils {

    public static Item[] loadSelfKeyItemsByCategoryId(Long userId, int categoryId) {
        ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
        List<Item> itemList = itemDao.queryBuilder().where(ItemDao.Properties.UserId.eq(userId)).list();
        if(itemList.size() == 0){
            return new Item[0];
        }
        Item[] items = new Item[0];
        int ind = 0;
        for(int i = 0; i < itemList.size(); i++){
            if (itemList.get(i).getCategoryId() == categoryId){
                items = Arrays.copyOf(items, items.length + 1);
                items[ind++] = itemList.get(i);
            }
        }
        if (ind == 0) {
            return new Item[0];
        }
        return items;
    }
}
