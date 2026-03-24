package br.com.foodhub.restaurantservice.infra.web.handler;

import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.restaurant.InvalidCnpjException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.restaurant.InvalidOpeningHoursException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(
            Throwable ex,
            DataFetchingEnvironment env
    ) {

        ProblemDetail problem;

        if (ex instanceof ResourceNotFoundException) {
            problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
            problem.setTitle("Recurso não encontrado");

        } else if (ex instanceof ResourceConflictException) {
            problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
            problem.setTitle("Conflito de recurso");

        } else if (ex instanceof BusinessRuleViolationException
                || ex instanceof RequiredFieldException
                || ex instanceof InvalidCnpjException
                || ex instanceof InvalidOpeningHoursException) {

            problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
            problem.setTitle("Erro de validação");

        } else if (ex instanceof org.springframework.security.access.AccessDeniedException) {

            problem = ProblemDetail.forStatusAndDetail(
                    HttpStatus.FORBIDDEN,
                    "Acesso negado"
            );
            problem.setTitle("Forbidden");

        }else {
            problem = ProblemDetail.forStatusAndDetail(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro inesperado. Contate o suporte."
            );
            problem.setTitle("Erro interno");
        }

        return GraphqlErrorBuilder.newError()
                .message(problem.getDetail())
                .path(env.getExecutionStepInfo().getPath())
                .extensions(Map.of(
                        "status", problem.getStatus(),
                        "title", problem.getTitle()
                ))
                .build();
    }
}