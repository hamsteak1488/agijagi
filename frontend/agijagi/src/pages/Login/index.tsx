import { useState } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';
import Button from '../../components/common/Button';
import Textfield from '../../components/common/Textfield';
import Typhography from '../../components/common/Typography';
import { IntroductionSlider } from '../../components/Login/IntroductionSlider';

const Container = styled.div(
  () => css`
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: end;
  `
);

const Moon = styled.div<{
  width: number;
  height: number;
  ratio: number;
  level: number;
  loginMode: boolean;
}>(
  (props) => css`
    position: fixed;
    width: ${props.width * 2}px;
    height: ${props.width * 2}px;
    border-radius: 56% 44% 53% 47% / 50% 48% 52% 50%;
    background-color: ${theme.color.primary[300]};
    top: ${props.width * -2 + props.height * 0.55}px;
    left: ${props.width * -0.5}px;
    transition: all 0.5s;
    transform: translate(
        ${props.loginMode ? 0 : props.width * -0.08 * props.level}px,
        ${props.loginMode ? props.height * -0.15 : 0}px
      )
      rotate(${props.loginMode ? 0 : props.level * 30}deg);
    z-index: 0;
  `
);

const LoginContainer = styled.div<{ width: number; loginMode: boolean }>(
  (props) => css`
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    max-width: 500px;
    width: 85%;
    margin: 0% 0% 15vh 0%;
    gap: 18px;
    z-index: 10;
    transition: all 0.75s;
    transform: translate(${props.loginMode ? props.width * -1 : 0}px, 0);
  `
);

const FormContainer = styled.div<{ width: number; loginMode: boolean }>(
  (props) => css`
    display: flex;
    flex-direction: column;
    align-items: center;
    max-width: 500px;
    width: 85%;
    margin: 0% 0% 15vh 0%;
    gap: 18px;
    z-index: 10;
    transition: all 0.75s;
    transform: translate(${props.loginMode ? 0 : props.width}px, 0);
  `
);

const PatternContainer = styled.div(
  () => css`
    position: relative;
    width: 100%;
    height: 100%;
  `
);

const Pattern1 = styled.div<{ ratio: number }>(
  (props) => css`
    position: absolute;
    width: ${100 * props.ratio}px;
    height: ${100 * props.ratio}px;
    border-radius: 57% 43% 58% 42% / 58% 41% 59% 42%;
    background-color: ${theme.color.primary[400]};
    top: 80%;
    left: 62%;
  `
);
const Pattern2 = styled.div<{ ratio: number }>(
  (props) => css`
    position: absolute;
    width: ${105 * props.ratio}px;
    height: ${105 * props.ratio}px;
    border-radius: 70% 30% 65% 35% / 36% 61% 39% 64%;
    background-color: ${theme.color.primary[400]};
    top: 42%;
    left: 55%;
  `
);
const Pattern3 = styled.div<{ ratio: number }>(
  (props) => css`
    position: absolute;
    width: ${120 * props.ratio}px;
    height: ${120 * props.ratio}px;
    border-radius: 49% 51% 39% 61% / 61% 50% 50% 39%;
    background-color: ${theme.color.primary[400]};
    top: 72%;
    left: 20%;
  `
);

const Pattern4 = styled.div<{ ratio: number }>(
  (props) => css`
    position: absolute;
    width: ${150 * props.ratio}px;
    height: ${150 * props.ratio}px;
    border-radius: 55% 45% 58% 42% / 48% 51% 49% 52%;
    background-color: ${theme.color.primary[400]};
    top: 60%;
    left: 40%;
  `
);

const Pattern5 = styled.div<{ ratio: number }>(
  (props) => css`
    position: absolute;
    width: ${150 * props.ratio}px;
    height: ${150 * props.ratio}px;
    border-radius: 100rem;
    background-color: ${theme.color.primary[400]};
    top: 36%;
    left: 24%;
  `
);

const Pattern6 = styled.div<{ ratio: number }>(
  (props) => css`
    position: absolute;
    width: ${110 * props.ratio}px;
    height: ${110 * props.ratio}px;
    border-radius: 56% 44% 53% 47% / 50% 48% 52% 50%;
    background-color: ${theme.color.primary[400]};
    top: 60%;
    left: 76%;
  `
);

export const Login = () => {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [level, setLevel] = useState<number>(0);
  const [loginMode, setLoginMode] = useState<boolean>(false);

  const widthRatio = window.innerWidth / 360;
  const width = window.innerWidth;
  const height = window.innerHeight;

  const handleButtonClick = () => {
    setLevel(level === 2 ? 0 : level + 1);
  };

  const handleActivateLogin = () => {
    setLoginMode(!loginMode);
  };

  const handleLevel = (level: number) => {
    setLevel(level);
  };

  return (
    <Container>
      <Moon
        width={width}
        height={height}
        ratio={widthRatio}
        level={level}
        loginMode={loginMode}
      >
        <PatternContainer>
          <Pattern1 ratio={widthRatio} />
          <Pattern2 ratio={widthRatio} />
          <Pattern3 ratio={widthRatio} />
          <Pattern4 ratio={widthRatio} />
          <Pattern5 ratio={widthRatio} />
          <Pattern6 ratio={widthRatio} />
        </PatternContainer>
      </Moon>
      <IntroductionSlider
        level={level}
        handleLevel={handleLevel}
        loginMode={loginMode}
      />
      <LoginContainer width={width} loginMode={loginMode}>
        <Button fullWidth={true} onClick={handleActivateLogin}>
          로그인
        </Button>
        <Button fullWidth={true}>회원가입</Button>
      </LoginContainer>

      <FormContainer width={width} loginMode={loginMode}>
        <Typhography size="7xl" weight="bold">
          로그인
        </Typhography>
        <Textfield
          size="lg"
          label="이메일"
          color="secondary"
          isColoredLabel={true}
          inputValue={email}
          setInputValue={setEmail}
          disabled={false}
        ></Textfield>
        <Textfield
          size="lg"
          label="비밀번호"
          isColoredLabel={true}
          inputValue={password}
          setInputValue={setPassword}
          type="password"
          disabled={false}
        ></Textfield>
        <Button
          size="md"
          color="primary"
          fullWidth={true}
          onClick={handleActivateLogin}
        >
          로그인
        </Button>
      </FormContainer>
    </Container>
  );
};
export default Login;
