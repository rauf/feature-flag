import {useMutation, useQuery} from '@tanstack/react-query';
import axios from 'axios';
import {Flag, FlagRequest, GetAllFlagsApiResponse} from "../shared/model";
import queryClient from "../config/client";

export const FETCH_FLAGS = 'FETCH_FLAGS';
export const FETCH_FLAG = 'FETCH_FLAG';

const apiUrl = '/api/v1/flags';

export const useAllFlagsWithSegments = () =>
    useQuery([FETCH_FLAGS], async () => {
        const res = await axios.get<GetAllFlagsApiResponse>(apiUrl);
        return res.data;
    });

export const useGetFlag = (onSuccess: any, name?: string) =>
    useQuery([FETCH_FLAG, name], async () => {
        const res = await axios.get<Flag>(`${apiUrl}/${name}`);
        return res.data;
    }, {
        onSuccess,
        enabled: !!name
    });

export const useCreateFlag = () =>
    useMutation(async (req: FlagRequest) => {
        const res = await axios.post<GetAllFlagsApiResponse>(apiUrl, req);
        await queryClient.invalidateQueries([FETCH_FLAGS]);
        return res.data;
    });

export const useUpdateFlag = () =>
    useMutation(async (req: FlagRequest) => {
        const res = await axios.put<GetAllFlagsApiResponse>(`${apiUrl}/${req.name}`, req);
        await queryClient.invalidateQueries([FETCH_FLAGS]);
        await queryClient.invalidateQueries([FETCH_FLAG, req.name]);
        return res.data;
    });

