import {useAllFlagsWithSegments} from "../../api/flag";
import FlagList from "../../components/flag-list";

export default function FlagPage() {

    const {data: res, isLoading, isError} = useAllFlagsWithSegments();

    if (isLoading) {
        return <div>Loading</div>
    }
    if (isError) {
        return <div>Error</div>
    }

    return (
        <div>
            <FlagList flags={res.flags}/>
        </div>
    )
}