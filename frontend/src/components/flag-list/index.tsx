import * as React from 'react';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import {Flag} from "../../shared/model";

interface FlagListProps {
    flags: Flag[]
}

export default function FlagList({flags}: FlagListProps) {

    if (!flags) {
        return <div>No flags present. Please create new flags</div>
    }

    return (
        <List
            sx={{width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}
            component="nav"
            aria-labelledby="nested-list-subheader"
            // subheader={
            //     <ListSubheader component="div" id="nested-list-subheader">
            //         Nested List Items
            //     </ListSubheader>
            // }
        >
            {flags.map(f => FlagListItem(f))}
        </List>
    )
}


export function FlagListItem(flag: Flag) {
    const [open, setOpen] = React.useState(false);

    const handleClick = () => {
        setOpen(!open);
    };

    return (
        <div key={flag.name}>
            <ListItemButton onClick={handleClick}>
                <ListItemText primary={flag.name} secondary={`Variants: ${flag.variants.map(v => v.name).join(", ")}`}/>
                {open ? <ExpandLess/> : <ExpandMore/>}
            </ListItemButton>
            <Collapse in={open} timeout="auto" unmountOnExit>
                {flag.segments.map(s => (
                    <div>
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
