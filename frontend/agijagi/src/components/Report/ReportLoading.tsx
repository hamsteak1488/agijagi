import React from 'react';
import styled from '@emotion/styled';
// import babyFlowerImage from '../assets/images/아기자기 로고2.png';
import babyFlowerImage from '../../assets/images/logo4.png';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background-color: #ffecb3;
  width: 100%;
  height: 100vh;
  position: relative;
`;

const BouncingImage = styled.img`
  width: 150px;
  height: 150px;
  animation: bounce 2s infinite;

  @keyframes bounce {
    0%, 20%, 50%, 80%, 100% {
      transform: translateY(0);
    }
    40% {
      transform: translateY(-30px);
    }
    60% {
      transform: translateY(-15px);
    }
  }
`;

const LoadingText = styled.span`
  margin-top: 20px;
  font-size: 16px;
`;


function Loading() {
  return (
    <Container>
      <BouncingImage src={babyFlowerImage} />
      <LoadingText>성장 분석 보고서가 생성중입니다..</LoadingText>
    </Container>
  );
}

export default Loading;
