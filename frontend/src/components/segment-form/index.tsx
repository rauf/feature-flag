import {useFieldArray, useForm} from "react-hook-form";
import {FormInputText} from "../form/FormInputText";
import {Segment} from "../../shared/model";
import {Button, Modal} from "@mui/material";
import Box from "@mui/material/Box";
import React, {useEffect, useState} from "react";
import {useCreateSegments, useGetAllSegmentsForFlag} from "../../api/segment";
import DynamicSegmentInput from "../form/DynamicDistributionsInput";
import {toast} from "react-toastify";

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
    const {control, formState: {errors}, handleSubmit, reset} = useForm({
        mode: "all",
    });
    const {
        data: segmentsData,
        isLoading,
        isError
    } = useGetAllSegmentsForFlag(flagName, (data: FormData) => reset({...data}));

    const [open, setOpen] = useState(false);
    const isNew = segmentsData === undefined || segmentsData.segments.length === 0

    const {fields, append, remove} = useFieldArray({
        control,
        name: "segments",
        rules: {
            required: "Please add at least one Segment",
        },
    });

    const {mutate, isLoading: mutateLoading, isSuccess: mutateSuccess} = useCreateSegments(() => {
        toast.error("Error!")
    });

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

    if (isLoading) {
        return <div>Loading</div>
    }
    if (isError) {
        return <div>Error</div>
    }

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
                                               disabled={!isNew}
                                               required
                                    // error={errors[i]?.name}
                                />
                            </Box>
                            <Box sx={styles.formField}>
                                <FormInputText control={control} name={`segments[${i}].priority`} label={"Priority"}
                                               required
                                    // error={errors[i]?.priority}
                                />
                            </Box>
                            <Box sx={styles.formField}>
                                <FormInputText control={control} name={`segments[${i}].rolloutPercentage`}
                                               label={"Rollout Percentage"}
                                               required
                                    // error={errors[i]?.rolloutPercentage}
                                />
                            </Box>
                            <Box sx={styles.formField}>
                                <FormInputText control={control} name={`segments[${i}].constraint`} label={"Constraint"}
                                               required
                                    // error={errors[i]?.constraint}
                                />
                            </Box>
                            <Box sx={styles.formField}>
                                <DynamicSegmentInput control={control} required index={i}/>
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
                        onClick={() => append({name: ""})}
                    >
                        Add a Segment
                    </Button>
                    <Button sx={styles.formField} onClick={handleSubmit(onSubmit)} variant={"contained"} disabled={mutateLoading}>
                        Submit
                    </Button>
                </Box>
            </Modal>
        </form>
    )

}