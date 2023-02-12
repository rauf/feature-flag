import React, {useState} from "react";
import DynamicInput from "react-dynamic-input";

export default function DynamicSegmentInput() {
    const [input, setInput] = useState([{name: ""}])
    const showState = () => {
        alert(input.map(item => item.name))
    }
    return (
        <div>
            <DynamicInput
                inputName="name"
                addButtonText="Add Another Input"
                setInput={setInput}
                input={input}
                onSubmit={showState}
                submitButtonText="Submit"
                addPosition="bottom"
                type="text"
                placeholderNumbered={true}
                toolTip={true}
                toolTipText="Add another input"
                label="My Input"
            />
            {input.map((item, index) => (
                <p key={(index + 1) * 2}>{item.name}</p>
            ))}
        </div>
    )
}