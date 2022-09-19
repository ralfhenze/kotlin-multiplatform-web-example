
export default class Task {
    constructor(
        public id: number | null,
        public state: string,
        public description: string,
    ) {}
}
