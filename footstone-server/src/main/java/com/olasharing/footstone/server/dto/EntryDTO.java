package com.olasharing.footstone.server.dto;

import lombok.Data;

/**
 * EntryDTO
 *
 * @author liuyan
 * @date 2019-03-05
 */
@Data
public class EntryDTO<K, V> {

    private K key;

    private V value;

    public EntryDTO() {
    }

    public EntryDTO(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
