import React from "react";
import {Controller, useFieldArray} from "react-hook-form";
import {Button, MenuItem, Select, TextField} from "@mui/material";
import Box from "@mui/material/Box";
import {FormInputText} from "./FormInputText";

interface DynamicSegmentsInputProps {
    control: any;
    disabled?: boolean;
    required?: boolean;
    error?: any;
    index: number;
    variants?: string[]
}

export default function DynamicDistributionsInput({
                                                      control,
                                                      disabled = false,
                                                      required,
                                                      error,
                                                      index,
                                                      variants
                                                  }: DynamicSegmentsInputProps) {
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
                    <Box key={item.id} sx={{display: "inline-flex", alignItems: 'center'}}>
                        <FormInputText control={control}
                                       name={`${getNestedKey(index, i)}.name`}
                                       label={`Distribution - ${i + 1}`}
                                       required
                        />
                        <Controller
                            control={control}
                            name={`${getNestedKey(index, i)}.variant`}
                            rules={{required: required}}
                            render={({field}) => (
                                <Select
                                    {...field}
                                    label="Variant"
                                    error={!!error}
                                    required={required}
                                    id="variant-name"
                                    fullWidth
                                    style={{height: 60}}
                                >
                                    {variants?.map(v => (
                                        <MenuItem value={v} key={v}>{v}</MenuItem>
                                    ))}
                                </Select>
                            )}
                        />

                        <FormInputText control={control}
                                       name={`${getNestedKey(index, i)}.percent`}
                                       label={`Percent - ${i + 1}`}
                                       required
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