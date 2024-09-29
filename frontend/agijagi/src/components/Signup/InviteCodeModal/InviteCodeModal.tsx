import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../../styles/theme';
import Typhography from '../../common/Typography';
import Textfield from '../../common/Textfield';
import Button from '../../common/Button';
import { useState } from 'react';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  border-radius: 1rem;
  width: 80vw;
  height: 50vh;
  max-width: 500px;
  max-height: 500px;
  background-color: ${theme.color.primary[50]};
`;

export const ModalButtonContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
`;

export const StyledTextfield = styled(Textfield)`
  width: 10vw !important;
`;

interface InviteCodeModal {
  closeModal: () => void;
}
export const InviteCodeModal = ({ closeModal }: InviteCodeModal) => {
  const [code, setCode] = useState<string>('');

  return (
    <Container>
      <Typhography size="2xl" weight="bold">
        초대코드 입력
      </Typhography>
      <StyledTextfield
        label="초대코드"
        size="md"
        inputValue={code}
        setInputValue={setCode}
      ></StyledTextfield>
      <ModalButtonContainer>
        <Button color="success">확인</Button>
        <Button
          color="danger"
          onClick={() => {
            closeModal();
          }}
        >
          취소
        </Button>
      </ModalButtonContainer>
    </Container>
  );
};
