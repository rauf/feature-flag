import {useMutation, useQuery} from '@tanstack/react-query';
import axios from 'axios';
import {GetAllSegmentsApiResponse} from "../shared/model";
import queryClient from "../config/client";

export const FETCH_SEGMENTS_FOR_FLAG = 'FETCH_SEGMENTS_FOR_FLAG';

const apiUrl = '/api/v1/flags';

export const useGetAllSegmentsForFlag = (flagName: string, onSuccess: any) =>
    useQuery([FETCH_SEGMENTS_FOR_FLAG, flagName], async () => {
        const res = await axios.get<GetAllSegmentsApiResponse>(`${apiUrl}/${flagName}/segments`);
        return res.data;
    }, {
        onSuccess
    });


export const useCreateSegments = (err: any) =>
    useMutation(async ({req, flagName}: { req: GetAllSegmentsApiResponse; flagName: string }) => {
        const res = await axios.post<GetAllSegmentsApiResponse>(`${apiUrl}/${flagName}/segments`, req);
        await queryClient.invalidateQueries([FETCH_SEGMENTS_FOR_FLAG, flagName]);
        return res.data;
    }, {
        onError: err
    });