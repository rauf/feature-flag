import React from "react";
import {Controller, useFieldArray} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import Box from "@mui/material/Box";
import {FormInputText} from "./FormInputText";

interface DynamicVariantsInputProps {
    control: any;
    disabled?: boolean;
    required?: boolean;
    error?: any
}

export default function DynamicVariantsInput({control, disabled = false, required, error}: DynamicVariantsInputProps) {
    const {fields, append, remove} = useFieldArray({
        control,
        name: "variants",
        rules: {
            required: "Please add at least one Variant",
        },
    });

    return (
        <div>
            {
                fields.map((item, i) => (
                    <Box key={item.id} sx={{display: "inline-flex", marginTop: '1rem'}}>
                        <FormInputText control={control}
                                       name={`variants[${i}].name`}
                                       label={"Variant"}
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
                Add a variant
            </Button>
            }
        </div>
    )
}