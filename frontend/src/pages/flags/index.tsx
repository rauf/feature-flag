import {useAllFlagsWithSegments} from "../../api/flag";
import FlagList from "../../components/flag-list";
import FlagForm from "../../components/flag-form";

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
            <FlagForm flag={undefined}/>
            <FlagList flags={res.flags}/>
        </div>
    )
}