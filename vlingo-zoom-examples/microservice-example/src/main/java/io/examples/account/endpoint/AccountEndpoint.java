package io.examples.account.endpoint;

import io.examples.account.domain.Account;
import io.vlingo.annotations.Resource;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.http.resource.RequestHandler1.Handler1;
import io.vlingo.http.resource.RequestHandler2;
import io.vlingo.resource.Endpoint;

import static io.vlingo.http.Response.Status.*;
import static io.vlingo.http.resource.ResourceBuilder.*;

@Resource
public class AccountEndpoint implements Endpoint {

    private static final String ENDPOINT_NAME = "Accounts API";
    private final AccountController accountController;

    public AccountEndpoint(AccountController accountController) {
        this.accountController = accountController;
    }

    @Override
    public String getName() {
        return AccountEndpoint.ENDPOINT_NAME;
    }

    @Override
    public RequestHandler[] getHandlers() {
        return new RequestHandler[]{
                get("/accounts")
                        .handle(() ->
                                getResponse(Ok, accountController::getAccounts))
                        .onError(this::getErrorResponse),
                get("/accounts/{id}")
                        .param(Long.class)
                        .handle((Handler1<Long>) (id) ->
                                getResponse(Ok, () -> accountController.getAccount(id)))
                        .onError(this::getErrorResponse),
                post("/accounts")
                        .body(Account.class)
                        .handle((Handler1<Account>) account ->
                                getResponse(Created, () -> accountController.createAccount(account)))
                        .onError(this::getErrorResponse),
                put("/accounts/{id}")
                        .param(Long.class)
                        .body(Account.class)
                        .handle((RequestHandler2.Handler2<Long, Account>) (id, account) ->
                                getResponse(Ok, () -> accountController.updateAccount(id, account)))
                        .onError(this::getErrorResponse),
                delete("/accounts/{id}")
                        .param(Long.class)
                        .handle((Handler1<Long>) (id) ->
                                getResponse(NoContent, () -> accountController.deleteAccount(id)))
                        .onError(this::getErrorResponse)

        };
    }

}
