import {TextField} from "@mui/material";
import {Controller, FieldError} from "react-hook-form";

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
            rules={{ required: required }}
            render={({field}) => (
                <TextField
                    {...field}
                    required={required}
                    fullWidth
                    onChange={field.onChange}
                    value={field.value}
                    disabled={disabled}
                    label={label}
                    error={!!error}
                    helperText={error && `${error?.message}`}
                />
            )}
        />

    );
}
