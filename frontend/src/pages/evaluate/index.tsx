import {useForm} from "react-hook-form";
import {EvaluateRequest} from "../../shared/model";
import React from "react";
import {Button} from "@mui/material";
import Box from "@mui/material/Box";
import {FormInputText} from "../../components/form/FormInputText";
import {useEvaluate} from "../../api/evaluate";
import {toast} from "react-toastify";

const styles = {
    boxStyle: {
        margin: 'auto',
        width: 500,
        p: 4,
    },
    formField: {
        marginTop: 2,
    }
};


export default function EvaluatePage() {
    const {control, formState: {errors}, handleSubmit} = useForm<EvaluateRequest>({mode: "all",});
    const onEvaluateSuccess = (msg: any) => toast.info("Result!" + JSON.stringify(msg));
    const {mutate, isLoading} = useEvaluate(onEvaluateSuccess);
    const onSubmit = (data: EvaluateRequest) => {
        const d = {
            ...data,
        }
        if (data.context) {
            d.context = JSON.parse(data.context)
        }
        mutate(d);
    };

    return (
        <form>
            <Box sx={styles.boxStyle}>
                <Box sx={styles.formField}>
                    <FormInputText control={control} name={"flagName"} label={"Flag Name"} required
                        // error={errors?.name}
                    />
                </Box>
                <Box sx={styles.formField}>
                    <FormInputText control={control} name={"id"} label={"Id"}
                        // error={errors.description}
                    />
                </Box>
                <Box sx={styles.formField}>
                    <FormInputText control={control} name={"context"} label={"Context"}
                        // error={errors.description}
                    />
                </Box>
                <Button sx={styles.formField} onClick={handleSubmit(onSubmit)} variant={"contained"}
                        disabled={isLoading}>
                    Submit
                </Button>

            </Box>
        </form>
    )
}