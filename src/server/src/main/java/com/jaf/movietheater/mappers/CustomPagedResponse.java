package com.jaf.movietheater.mappers;

import java.util.Collection;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;

public class CustomPagedResponse<T> {
    private Collection<T> data;

    private PagedModel.PageMetadata page;

    private Links links;

    public CustomPagedResponse(Collection<T> data, PageMetadata page, Links links) {
        this.data = data;
        this.page = page;
        this.links = links;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public PagedModel.PageMetadata getPage() {
        return page;
    }

    public void setPage(PagedModel.PageMetadata page) {
        this.page = page;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
