import React, { useState, useRef } from 'react';
import Typhography from '../Typography';
import * as s from './Textfield.style';
import { TextfieldColor } from './Textfield.types';

interface TextfieldProps {
  color: TextfieldColor;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export const Textfield = ({ color, onChange }: TextfieldProps) => {
  const [focused, setFocused] = useState(false);
  const [isFloating, setIsFloating] = useState<boolean>(false);
  const inputRef = useRef<HTMLInputElement>(null);

  const handleFocus = () => {
    setFocused(true);
    setIsFloating(true);
  };

  const hadleblur = () => {
    setFocused(false);
    if (inputRef.current?.value === '') setIsFloating(false);
  };
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIsFloating(() => focused || e.target.value !== '');
    if (onChange) onChange(e);
  };

  return (
    <s.TextfieldContainer color={color}>
      <s.Label isFloating={isFloating}>이름</s.Label>
      <s.Textfield
        ref={inputRef}
        onFocus={handleFocus}
        onBlur={hadleblur}
        onChange={handleChange}
      />
    </s.TextfieldContainer>
  );
};
