package pl.mt.mortalis.page;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageService {
    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Optional<Page> findByUrl(String url) {
        return pageRepository.findByUrl(url);
    }
}
