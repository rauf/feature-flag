export interface Variant {
    name: string;
}

export interface Distribution {
    name: string;
    percent: number;
    variant: string;
}

export interface Segment {
    name: string;
    priority: number;
    rolloutPercentage: number;
    constraint: string;
    distributions: Distribution[];
}

export interface Flag {
    name: string;
    description: string;
    enabled: boolean;
    variants: Variant[];
    segments: Segment[];
}

export interface GetAllFlagsApiResponse {
    flags: Flag[];
}

export interface FlagRequest {
    name: string;
    description: string;
    enabled: boolean;
    variants: string[];
}



