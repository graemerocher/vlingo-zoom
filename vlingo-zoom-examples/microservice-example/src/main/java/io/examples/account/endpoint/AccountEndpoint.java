package io.examples.account.endpoint;

import io.examples.account.domain.Account;
import io.examples.account.domain.AccountService;
import io.vlingo.annotations.Resource;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.http.resource.RequestHandler1.Handler1;
import io.vlingo.http.resource.RequestHandler2;
import io.vlingo.resource.Endpoint;

import static io.vlingo.http.Response.Status.*;
import static io.vlingo.http.resource.ResourceBuilder.*;

/**
 * This {@link AccountEndpoint} exposes a REST HTTP API that maps resource request-response handlers to operations
 * contained in the {@link AccountService}. This grouping of API resources is semantically versioned and allows
 * you to more easily evolve your API definition without breaking consumers.
 * <p>
 * This {@link Endpoint} forms an anti-corruption layer between consuming services and this microservice's
 * {@link Account} API.
 *
 * @author Kenny Bastani
 */
@Resource
public class AccountEndpoint implements Endpoint {

    private static final String ENDPOINT_VERSION = "1.0.0";
    private static final String ENDPOINT_NAME = "Accounts REST API";
    private final AccountService accountService;

    /**
     * Creates a new instance of the {@link AccountEndpoint} with a dependency injected {@link AccountService}.
     *
     * @param accountService is anti-corruption layer that exposes service operations on the {@link Account} aggregate.
     */
    public AccountEndpoint(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Get the full name and version of this {@link Endpoint}.
     *
     * @return a {@link String} representing the full name and semantic version of this {@link Endpoint} definition.
     */
    @Override
    public String getName() {
        return AccountEndpoint.ENDPOINT_NAME + " v" + AccountEndpoint.ENDPOINT_VERSION;
    }

    /**
     * Get the semantic version of this {@link Endpoint}.
     *
     * @return a {@link String} representing the semantic version of this HTTP {@link Endpoint} definition.
     */
    public static String getEndpointVersion() {
        return ENDPOINT_VERSION;
    }

    /**
     * Get an array of {@link RequestHandler}s that expose HTTP mappings on commands in the {@link AccountService}.
     *
     * @return an array of {@link RequestHandler}s.
     */
    @Override
    public RequestHandler[] getHandlers() {
        return new RequestHandler[]{
                get("/accounts")
                        .handle(() ->
                                getResponse(Ok, accountService::getAccounts))
                        .onError(this::getErrorResponse),
                get("/accounts/{id}")
                        .param(Long.class)
                        .handle((Handler1<Long>) (id) ->
                                getResponse(Ok, () -> accountService.getAccount(id)))
                        .onError(this::getErrorResponse),
                post("/accounts")
                        .body(Account.class)
                        .handle((Handler1<Account>) account ->
                                getResponse(Created, () -> accountService.createAccount(account)))
                        .onError(this::getErrorResponse),
                put("/accounts/{id}")
                        .param(Long.class)
                        .body(Account.class)
                        .handle((RequestHandler2.Handler2<Long, Account>) (id, account) ->
                                getResponse(Ok, () -> accountService.updateAccount(id, account)))
                        .onError(this::getErrorResponse),
                delete("/accounts/{id}")
                        .param(Long.class)
                        .handle((Handler1<Long>) (id) ->
                                getResponse(NoContent, () -> accountService.deleteAccount(id)))
                        .onError(this::getErrorResponse)

        };
    }
}
