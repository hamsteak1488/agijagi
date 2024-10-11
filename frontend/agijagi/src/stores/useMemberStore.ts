import { create } from 'zustand';

interface State {
  memberId: number;
}

interface Action {
  updateMemberId: (memberId: State['memberId']) => void;
}

const useMemberStore = create<State & Action>((set) => ({
  memberId: 0,
  updateMemberId: (memberId) => set(() => ({ memberId })),
}));

export default useMemberStore;
