import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../../styles/theme';
import Typhography from '../../../components/common/Typography';
import WelcomeImg from '../../../assets/images/adult.png';
import Button from '../../../components/common/Button';
import { useEffect, useState } from 'react';
import useModal from '../../../hooks/useModal';
import Textfield from '../../../components/common/Textfield';
import { InviteCodeModal } from '../../../components/Signup/InviteCodeModal/InviteCodeModal';
import { useNavigate } from 'react-router-dom';

export const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 5vh;
  background-color: ${theme.color.primary[50]};
`;

export const BabyImg = styled.img`
  width: 50%;
  aspect-ratio: 1;
  max-width: 500px;
  max-height: 500px;
`;

export const Introduction = styled.div<{ animationKey: boolean }>(
  (props) => css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    gap: 3vh;
    transition: all 1.5s;
    transform: translate(${props.animationKey ? 0 : 100}vw, 0);
  `
);

export const ButtonContainer = styled.div<{ animationKey: boolean }>(
  (props) => css`
    display: flex;
    flex-direction: column;
    width: 80%;
    gap: 3vh;
    text-align: center;
    transition: all 2s;
    transform: translate(0, ${props.animationKey ? 0 : 100}vh);
  `
);

export const SkipButton = styled.div`
  color: ${theme.color.greyScale[500]};
  text-decoration: underline;
`;

export const Welcome = () => {
  const [initialRender, setInitialRender] = useState<boolean>(false);

  const modal = useModal();
  const navigator = useNavigate();

  useEffect(() => {
    setInitialRender(true);
  }, []);

  const closeModal = () => {
    modal.pop();
  };

  const handleModal = () => {
    modal.push({
      children: <InviteCodeModal closeModal={closeModal} />,
    });
  };
  return (
    <Container>
      <Introduction animationKey={initialRender}>
        <Typhography size="6xl" weight="bold">
          어서오세요!
        </Typhography>
        <BabyImg src={WelcomeImg} />
        <Typhography size="xl" weight="bold">
          다른 사람으로부터 전달받은
          <br />
          초대 코드가 있나요?
        </Typhography>
      </Introduction>

      <ButtonContainer animationKey={initialRender}>
        <Button size="md" fullWidth={true} onClick={handleModal}>
          <Typhography color="white">네 (초대코드 등록)</Typhography>
        </Button>
        <Button
          size="md"
          color="secondary"
          fullWidth={true}
          onClick={() => navigator('/babyform')}
        >
          <Typhography color="white">아니오 (아기 등록)</Typhography>
        </Button>
        <SkipButton onClick={() => navigator('/main')}>
          <Typhography color="greyScale" shade="500">
            건너뛰기
          </Typhography>
        </SkipButton>
      </ButtonContainer>
    </Container>
  );
};
