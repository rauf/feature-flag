import {useQuery} from '@tanstack/react-query';
import axios from 'axios';
import {GetAllFlagsApiResponse} from "../shared/model";

export const FETCH_FLAGS = 'FETCH_FLAGS';

const apiUrl = '/api/v1/flags';

export const useAllFlagsWithSegments = () =>
    useQuery([FETCH_FLAGS], async () => {
        const res = await axios.get<GetAllFlagsApiResponse>(apiUrl);
        return res.data;
    });


