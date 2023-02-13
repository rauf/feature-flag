import React from "react";
import {Controller, useFieldArray} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import Box from "@mui/material/Box";

interface DynamicSegmentsInputProps {
    control: any;
    disabled?: boolean;
    required?: boolean;
    error?: any;
    index: number
}

export default function DynamicDistributionsInput({control, disabled = false, required, error, index}: DynamicSegmentsInputProps) {
    const {fields, append, remove} = useFieldArray({
        control,
        name: getKey(index),
        rules: {
            required: "Please add at least one Distribution",
        },
    });

    return (
        <div>
            {
                fields.map((item, i) => (
                    <Box key={item.id} sx={{display: "inline-flex"}}>
                        <Controller
                            control={control}
                            name={`${getNestedKey(index, i)}.name`}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    disabled={disabled}
                                    error={!!error}
                                    helperText={error && `${error.message}`}
                                    margin="normal"
                                    required={required}
                                    fullWidth
                                    id="distributions-name"
                                    label={`Distribution - ${i + 1}`}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name={`${getNestedKey(index, i)}.variant`}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    disabled={disabled}
                                    error={!!error}
                                    helperText={error && `${error.message}`}
                                    margin="normal"
                                    required={required}
                                    fullWidth
                                    id="distributions"
                                    label={`Variant - ${i + 1}`}
                                />
                            )}
                        />

                        <Controller
                            control={control}
                            name={`${getNestedKey(index, i)}.percent`}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    disabled={disabled}
                                    error={!!error}
                                    helperText={error && `${error.message}`}
                                    margin="normal"
                                    required={required}
                                    fullWidth
                                    id="distributions"
                                    label={`Percent - ${i + 1}`}
                                />
                            )}
                        />

                        {
                            !disabled && <div onClick={() => remove(i)}>
                                X
                            </div>
                        }
                    </Box>
                ))
            }
            {!disabled && <Button
                variant="outlined"
                fullWidth
                onClick={() => append({name: ""})}
            >
                Add a Distribution
            </Button>
            }
        </div>
    )
}

function getKey(index: number) {
    return `segments[${index}].distributions`;
}

function getNestedKey(index: number, nestedIndex: number) {
    return `segments[${index}].distributions[${nestedIndex}]`;
}