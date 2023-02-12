import * as React from 'react';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import {Flag} from "../../shared/model";
import FlagForm from "../flag-form";
import {useState} from "react";

interface FlagListProps {
    flags: Flag[]
}

export default function FlagList({flags}: FlagListProps) {
    const [openState, setOpenState] = useState<Record<string, boolean>>({});

    if (!flags) {
        return <div>No flags present. Please create new flags</div>
    }

    function setOpen(key: string, open: boolean) {
        setOpenState({
            ...openState,
            [key]: open
        });
    }

    return (
        <List
            sx={{width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}
            component="nav"
            aria-labelledby="nested-list-subheader"
        >
            {flags.map(f => <FlagListItem key={f.name} flag={f} open={openState[f.name]} setOpen={setOpen}/>)}
        </List>
    )
}

interface FlagListItemProps {
    flag: Flag;
    open: boolean;
    setOpen: any
}

export function FlagListItem({flag, open, setOpen}: FlagListItemProps) {

    const handleClick = () => {
        setOpen(flag.name, !open);
    };

    return (
        <div>
            <ListItemButton onClick={handleClick}>
                <ListItemText primary={flag.name} secondary={`Variants: ${flag.variants.map(v => v.name).join(", ")}`}/>
                {open ? <ExpandLess/> : <ExpandMore/>}
            </ListItemButton>
            <Collapse in={open} timeout="auto" unmountOnExit>
                {<FlagForm flag={flag}/>}
                {flag.segments.map(s => (
                    <div key={s.name}>
                        <List component="div" disablePadding>
                            <ListItemButton sx={{pl: 4}}>
                                <ListItemText primary={s.name}>
                                    {s.name}
                                </ListItemText>
                            </ListItemButton>
                        </List>
                    </div>
                ))}
            </Collapse>
        </div>
    );
}
