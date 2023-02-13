import {useForm} from "react-hook-form";
import {FormInputText} from "../form/FormInputText";
import {Flag} from "../../shared/model";
import DynamicVariants from "../form/DynamicVariantsInput";
import {Button, Modal} from "@mui/material";
import Box from "@mui/material/Box";
import {useEffect, useState} from "react";
import {useCreateFlag, useUpdateFlag} from "../../api/flag";

interface FlagFormProps {
    flag?: Flag;
}


const styles = {
    boxStyle: {
        position: 'absolute' as 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    },
    formField: {
        marginTop: 2,
    }
};


export default function FlagForm({flag}: FlagFormProps) {
    const isNew = flag === undefined;
    const {control, formState: {errors}, handleSubmit, reset} = useForm({
        defaultValues: flag, mode: "all",
    });
    const [open, setOpen] = useState(false);
    const {mutate: createMutate, isLoading: createMutationLoading, isSuccess: createMutationSuccess} = useCreateFlag();
    const {mutate: updateMutate, isLoading: updateMutationLoading, isSuccess: updateMutationSuccess} = useUpdateFlag();
    const updating = createMutationLoading || updateMutationLoading;

    useEffect(() => {
        if (createMutationSuccess || updateMutationSuccess) {
            reset();
            setOpen(false);
        }
    }, [createMutationSuccess, updateMutationSuccess, reset]);

    const onSubmit = (data: Flag) => {
        const entity = {
            ...data,
            variants: data.variants.map(v => v.name),
            segments: undefined
        };
        if (isNew) {
            createMutate(entity);
        } else {
            updateMutate(entity)
        }
    };

    return (
        <form>
            <Button onClick={() => setOpen(true)}>{isNew ? 'Create Flag' : 'Edit Flag'}</Button>
            <Modal
                open={open}
                onClose={() => setOpen(false)}
            >
                <Box sx={styles.boxStyle}>
                    <Box sx={styles.formField}>
                        <FormInputText control={control} name={"name"} label={"Flag Name"} disabled={!isNew} required
                                       error={errors?.name}/>
                    </Box>
                    <Box sx={styles.formField}>
                        <FormInputText control={control} name={"description"} label={"Description"} required
                                       error={errors.description}/>
                    </Box>
                    <Box sx={styles.formField}>
                        <FormInputText control={control} name={"enabled"} label={"Enabled"} required
                                       error={errors.enabled}/>
                    </Box>
                    <Box sx={styles.formField}>
                        <DynamicVariants control={control} disabled={!isNew} required error={errors.variants}/>
                    </Box>
                    <Button sx={styles.formField} onClick={handleSubmit(onSubmit)} variant={"contained"} disabled={updating}>
                        Submit
                    </Button>
                </Box>
            </Modal>
        </form>
    )

}