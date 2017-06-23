package dao;

import model.LibraryItem;

/**
 * Created by czyczk on 2017-6-23.
 */
public interface ILibraryItemDao<E> {
    void add(E item);
    void delete(E item);
    void update(E oldItem, E newItem);
}
