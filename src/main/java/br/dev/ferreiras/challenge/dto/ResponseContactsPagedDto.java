package br.dev.ferreiras.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@JsonPropertyOrder({ "totalElements", "totalPages", "size", "numberOfElements", "number", "first", "last", "empty", "content" })
public class ResponseContactsPagedDto implements Page {

    private Page page;
    /**
     * @return 
     */
    @Override
    public int getTotalPages() {
        return page.getTotalPages();
    }

    /**
     * @return 
     */
    @Override
    public long getTotalElements() {
        return page.getTotalElements();
    }

    /**
     * @param converter 
     * @return
     */
    @JsonIgnore
    @Override
    public Page map(Function converter) {
        return page.map(converter);
    }

    /**
     * @return 
     */
    @Override
    public int getNumber() {
        return page.getNumber();
    }

    /**
     * @return 
     */
    @Override
    public int getSize() {
        return page.getSize();
    }

    /**
     * @return 
     */
    @Override
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    /**
     * @return 
     */
    @Override
    public List getContent() {
        return page.getContent();
    }

    /**
     * @return 
     */
    @Override
    public boolean hasContent() {
        return page.hasContent();
    }

    /**
     * @return 
     */
    @Override
    @JsonIgnore
    public Sort getSort() {
        return page.getSort();
    }

    /**
     * @return 
     */
    @Override
    public boolean isFirst() {
        return page.isFirst();
    }

    /**
     * @return 
     */
    @Override
    public boolean isLast() {
        return page.isLast();
    }

    /**
     * @return 
     */
    @Override
    @JsonIgnore
    public boolean hasNext() {
        return page.hasNext();
    }

    /**
     * @return 
     */
    @Override
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    /**
     * @return 
     */
    @Override
    @JsonIgnore
    public Pageable nextPageable() {
        return nextPageable();
    }

    /**
     * @return 
     */
    @Override
    @JsonIgnore
    public Pageable previousPageable() {
        return previousPageable();
    }

    /**
     * @return 
     */
    @Override
    @JsonIgnore

    public Iterator iterator() {
        return page.iterator();
    }
}
