import {useMutation} from "@tanstack/react-query";
import axios from "axios";
import {EvaluateRequest} from "../shared/model";

const apiUrl = '/api/v1/evaluate';

export const useEvaluate = (onSuccess: any) =>
    useMutation(async (req: EvaluateRequest,) => {
        const res = await axios.post<EvaluateRequest>(apiUrl, req);
        return res.data;
    }, {
        onSuccess
    });