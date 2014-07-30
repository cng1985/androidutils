package com.ada.cache;

import android.content.ContentValues;
import android.database.Cursor;

public interface SilkCursorItem<Type> extends SilkComparable<Type> {

    public ContentValues getContentValues();

    public Type convert(Cursor cursor);

    public String getColumns();
}
