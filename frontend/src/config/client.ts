import {
    InvalidateOptions,
    InvalidateQueryFilters,
    QueryClient,
    QueryKey,
} from "@tanstack/react-query";

const queryClient = new QueryClient();

export function invalidateQuery(
    queryKey?: QueryKey,
    filters?: InvalidateQueryFilters,
    options?: InvalidateOptions
) {
    return queryClient.invalidateQueries(queryKey, filters, options);
}

export default queryClient;
