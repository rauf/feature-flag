import {useFieldArray, useForm} from "react-hook-form";
import {FormInputText} from "../form/FormInputText";
import {Segment} from "../../shared/model";
import {Button, Modal} from "@mui/material";
import Box from "@mui/material/Box";
import React, {useEffect, useState} from "react";
import {useCreateSegments, useGetAllSegmentsForFlag} from "../../api/segment";
import DynamicSegmentInput from "../form/DynamicDistributionsInput";
import {toast} from "react-toastify";
import {useGetFlag} from "../../api/flag";

interface SegmentFormProps {
    flagName: string;
}

const styles = {
    boxStyle: {
        position: 'absolute' as 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 600,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        marginTop: '8rem',
        p: 4,
    },
    formField: {
        marginTop: 2,
    },
    segmentBox: {
        border: '2px solid black',
        padding: '1.5rem',
        marginTop: '0.5rem'
    },
    modal: {
        overflow: 'auto'
    }
};

interface FormData {
    segments?: Segment[]
}

export default function SegmentForm({flagName}: SegmentFormProps) {
    const {control, formState: {errors}, handleSubmit, reset} = useForm<FormData>({
        mode: "all",
    });
    const {fields, append, remove} = useFieldArray<FormData>({
        control,
        name: "segments",
        rules: {
            required: "Please add at least one Segment",
        },
    });

    const onGetSegmentsSuccess = (data: FormData) => reset({...data});
    const {data: segmentsData, isLoading, isError} = useGetAllSegmentsForFlag(flagName, onGetSegmentsSuccess);
    const [open, setOpen] = useState(false);
    const isNew = segmentsData === undefined || segmentsData.segments.length === 0
    const onCreateSegmentsErr = (msg: any) => toast.error("Error!" + msg);
    const {mutate, isLoading: mutateLoading, isSuccess: mutateSuccess} = useCreateSegments(onCreateSegmentsErr);
    const {data: flag, isLoading: flagLoading, isError: flagError} = useGetFlag(undefined, flagName);

    useEffect(() => {
        if (mutateSuccess) {
            reset();
            setOpen(false);
        }
    }, [mutateSuccess, reset]);

    const onSubmit = (data: FormData) => {
        if (!data || !data.segments) {
            return
        }
        mutate({req: {segments: data.segments!}, flagName});
    };

    if (isLoading || flagLoading) {
        return <div>Loading</div>
    }
    if (isError || flagError) {
        return <div>Error</div>
    }

    const variants = flag?.variants?.map(v => v.name)
    console.log(`errors: ${JSON.stringify(errors)}`)

    return (
        <form>
            <Button onClick={() => setOpen(true)}>{isNew ? 'Create Segment' : 'Edit Segment'}</Button>
            <Modal
                open={open}
                onClose={() => setOpen(false)}
                sx={styles.modal}
            >
                <Box component="div" sx={styles.boxStyle}>
                    {fields?.map((s, i) => (
                        <Box key={s.id} sx={styles.segmentBox}>
                            <Box sx={styles.formField}>
                                <FormInputText control={control} name={`segments[${i}].name`} label={"Segment Name"}
                                               required
                                               error={errors?.segments?.[i]?.name}
                                />
                            </Box>
                            <Box sx={styles.formField}>
                                <FormInputText control={control} name={`segments[${i}].priority`} label={"Priority"}
                                               required
                                               error={errors?.segments?.[i]?.priority}
                                />
                            </Box>
                            <Box sx={styles.formField}>
                                <FormInputText control={control} name={`segments[${i}].rolloutPercentage`}
                                               label={"Rollout Percentage"}
                                               required
                                               error={errors?.segments?.[i]?.rolloutPercentage}
                                />
                            </Box>
                            <Box sx={styles.formField}>
                                <FormInputText control={control} name={`segments[${i}].constraint`} label={"Constraint"}
                                               error={errors?.segments?.[i]?.constraint}
                                />
                            </Box>
                            <Box sx={styles.formField}>
                                <DynamicSegmentInput control={control} required index={i} variants={variants}/>
                            </Box>
                            <Button onClick={() => remove(i)}>
                                Remove Segment
                            </Button>
                        </Box>
                    ))
                    }
                    <Button
                        variant="outlined"
                        fullWidth
                        onClick={() => append({} as Segment)}
                    >
                        Add a Segment
                    </Button>
                    <Button sx={styles.formField} onClick={handleSubmit(onSubmit)} variant={"contained"}
                            disabled={mutateLoading}>
                        Submit
                    </Button>
                </Box>
            </Modal>
        </form>
    )
}