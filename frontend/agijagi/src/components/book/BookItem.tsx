import React, { useState } from 'react';
import styled from '@emotion/styled';

type BookItemProps = {
  image: string;
  id: number;
};

const BookWrapper = styled.div`
  min-width: 150px;
  height: 220px;
  background-color: #f7f7f7;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 14px 14px 10px rgba(0, 0, 0, 0.2);
  margin-right: 25px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;

  &:hover {
    transform: translateY(-10px);
    box-shadow: 14px 14px 10px rgba(0, 0, 0, 0.3);
  }
`;

const BookImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
`;

const BookItem = ({ image, id }: BookItemProps) => {
  // 책 각각을 선택했을 때의 액션 넣기

  return (
    <BookWrapper>
        <BookImage src={image} alt="Book Cover" />
    </BookWrapper>
  );
};

export default BookItem;
