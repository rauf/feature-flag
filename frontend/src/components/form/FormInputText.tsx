import {TextField} from "@mui/material";
import {Controller, FieldError} from "react-hook-form";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;

interface FormInputTextProps {
    name: string;
    control: any;
    label: string;
    disabled?: boolean;
    required?: boolean;
    error?: FieldError
}

export const FormInputText = ({name, control, label, disabled = false, required, error}: FormInputTextProps) => {
    return (
        <Controller
            name={name}
            control={control}
            render={({field: {onChange, value}}) => (
                <TextField
                    required={required}
                    fullWidth
                    onChange={onChange}
                    value={value}
                    disabled={disabled}
                    label={label}
                    error={!!error}
                    helperText={error && `${error?.message}`}
                />
            )}
        />

    );
}
