package com.quadbaze.headstart.services;

import com.quadbaze.headstart.components.RemoteEndpoint;
import com.quadbaze.headstart.domain.enums.QueryField;
import com.quadbaze.headstart.domain.models.Book;
import com.quadbaze.headstart.domain.models.ResponseKeys;
import com.quadbaze.headstart.logging.LoggerService;
import com.quadbaze.headstart.utils.ResourceUtil;
import com.quadbaze.headstart.utils.json.GsonUtil;
import com.quadbaze.headstart.utils.json.Payload;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:18 AM
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private LoggerService LOGGER;

    private ResourceUtil resourceUtil;

    private RemoteEndpoint remoteEndpoint;
    private ApiRequestSenderService apiRequestSenderService;

    @Autowired
    public BookServiceImpl(ResourceUtil resourceUtil, RemoteEndpoint remoteEndpoint, ApiRequestSenderService apiRequestSenderService){
        this.resourceUtil = resourceUtil;
        this.remoteEndpoint = remoteEndpoint;
        this.apiRequestSenderService = apiRequestSenderService;
    }

    /**
     * Service layer method for searching - This method contacts the remote API, converts
     * the xml response to json and extracts the required book details and
     * @param query
     * @param queryField
     * @param page
     * @return
     */
    @Override
    public Page<Book> findBooks(String query, QueryField queryField, Integer page) {
        LOGGER.info("Fetching Books ... ");

        Page<Book> bookPage = Page.empty();

        Payload payload = new Payload()
                .set("key", remoteEndpoint.getApiKey())
                .set(QueryField.Keys.q.name(), query)
                .set(QueryField.Keys.field.name(), queryField.name())
                .set(QueryField.Keys.page.name(), page);

        String title = "";
        String authorName = "";
        String imageUrl = "";
        int pageSize;
        int totalBooksCount;
        if(!payload.isEmpty()){
            //JSONObject response = apiRequestSenderService.sendRequest(remoteEndpoint.getEndPointUrl(), HttpMethod.GET, payload, MediaType.APPLICATION_JSON);

            JSONObject response = getMockJsonResponse();

            if(!response.isEmpty()){
                Object booksResponseObject = response.get(ResponseKeys.BOOKS_RESPONSE);
                if(booksResponseObject instanceof JSONObject){
                    JSONObject booksResponse = (JSONObject)booksResponseObject;
                    Object searchObject = booksResponse.get(ResponseKeys.SEARCH);
                    if(searchObject instanceof JSONObject){
                        JSONObject search = (JSONObject)searchObject;
                        Object resultsObject = search.get(ResponseKeys.RESULTS);

                        pageSize = (search.getInt(ResponseKeys.RESULTS_END) - search.getInt(ResponseKeys.RESULTS_START)) + 1;
                        totalBooksCount = search.getInt(ResponseKeys.TOTAL_RESULTS);

                        if(resultsObject instanceof JSONObject){
                            JSONObject results = (JSONObject)resultsObject;
                            Object worksObject = results.get(ResponseKeys.WORK);
                            if(worksObject instanceof JSONArray){
                                JSONArray works = (JSONArray)worksObject;

                                List<Book> books = new ArrayList<>();
                                for(Object work : works){
                                    if(work instanceof JSONObject){
                                        JSONObject detail = (JSONObject)work;
                                        Object bookObject = detail.get(ResponseKeys.BEST_BOOK);
                                        if(bookObject instanceof JSONObject){
                                            JSONObject book = (JSONObject)bookObject;

                                            imageUrl = book.getString(ResponseKeys.SMALL_IMAGE_URL);
                                            title = book.getString(ResponseKeys.TITLE);

                                            Object authorObject = book.get(ResponseKeys.AUTHOR);
                                            if(authorObject instanceof JSONObject){
                                                JSONObject author = (JSONObject)authorObject;
                                                authorName = author.getString(ResponseKeys.NAME);

                                                books.add(new Book(imageUrl, title, authorName));
                                            }
                                        }
                                    }
                                }
                                bookPage = new PageImpl<>(books, PageRequest.of(page, pageSize), totalBooksCount);
                            }
                        }
                    }
                }
            }
        }

        LOGGER.info(String.format("%s Books were found for - Query: %s | Field: %s", bookPage.getContent().size(), query, queryField.name()));
        return bookPage;
    }

    /**
     * Gets a list of all the page numbers for the specified page
     * @param bookPage
     * @return
     */
    @Override
    public List<Integer> getPageNumbers(Page<Book> bookPage){
        int totalPages = bookPage.getTotalPages();
        return totalPages > 0 ? IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList()) : Collections.EMPTY_LIST;
    }

    private JSONObject getMockJsonResponse(){
        String jsonString = resourceUtil.getResourceContent("classpath:mocks/books.json");
        return StringUtils.isNotBlank(jsonString) ? new JSONObject(GsonUtil.toMap(jsonString)) : new JSONObject();
    }

}
