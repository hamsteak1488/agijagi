import styled from '@emotion/styled';
import theme from '../../../../styles/theme';
import { FollowerResponse } from '../../../../types/child';
import Typhography from '../../../common/Typography';

export interface FollowerItemProps {
  member: FollowerResponse;
}

export const FollowerItem = ({ member }: FollowerItemProps) => {
  return <Typhography size="xl">{member.nickname}</Typhography>;
};
