package com.jaf.movietheater.response;

import java.util.Collection;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;

public class CustomResponseData<T> {
    private Collection<T> data;

    private Links links;

    public CustomResponseData(Collection<T> data, Links links, PageMetadata page) {
        this.data = data;
        this.links = links;
        this.page = page;
    }

    private PagedModel.PageMetadata page;

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public PagedModel.PageMetadata getPage() {
        return page;
    }

    public void setPage(PagedModel.PageMetadata page) {
        this.page = page;
    }

}
