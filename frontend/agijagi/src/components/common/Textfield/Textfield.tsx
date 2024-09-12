import { type } from 'os';
import React, { useRef, useState } from 'react';
import * as s from './Textfield.style';
import {
  TextfieldColor,
  TextfieldSize,
  ValidationState,
} from './Textfield.types';

interface TextfieldProps {
  label: string;
  inputValue: string;
  setInputValue: React.Dispatch<React.SetStateAction<string>>;
  size?: TextfieldSize;
  color?: TextfieldColor;
  fullWidth?: boolean;
  isColoredLabel?: boolean;
  helpText?: string;
  checkText?: string;
  warningText?: string;
  type?: string;
  validationFunction?: (text: string) => ValidationState;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

//test Function
function validateInput(input: string): ValidationState {
  if (input.trim() === '') {
    return 'normal';
  }
  if (input.length >= 2 && input.length <= 8) {
    return 'success';
  }
  return 'danger';
}

const Textfield = ({
  label,
  size = 'md',
  color = 'primary',
  fullWidth = false,
  isColoredLabel = false,
  helpText = '필수항목이에요',
  checkText = '올바르게 입력되었어요',
  warningText = '이름은 필수 항목이에요',
  inputValue = '',
  type = '',
  setInputValue,
  validationFunction = validateInput,
  onChange,
}: TextfieldProps) => {
  const [focused, setFocused] = useState(false);
  const [isFloating, setIsFloating] = useState<boolean>(false);
  const [fieldState, setFieldState] = useState<ValidationState>('normal');
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
    setInputValue(e.target.value);
    setFieldState(validationFunction(e.target.value));
    if (onChange) onChange(e);
  };

  const bottomText = () => {
    switch (fieldState) {
      case 'danger':
        return warningText;
      case 'success':
        return checkText;
      case 'normal':
        return helpText;
    }
  };

  return (
    <>
      <s.TextfieldContainer
        size={size}
        fieldState={fieldState}
        color={color}
        fullWidth={fullWidth}
      >
        <s.Label
          isFloating={isFloating}
          size={size}
          color={color}
          fieldState={fieldState}
          isColoredLabel={isColoredLabel}
        >
          {label}
        </s.Label>
        <s.Textfield
          type={type}
          value={inputValue}
          isColoredLabel={isColoredLabel}
          color={color}
          fieldSize={size}
          fieldState={fieldState}
          ref={inputRef}
          onFocus={handleFocus}
          onBlur={hadleblur}
          onChange={handleChange}
        />
      </s.TextfieldContainer>
      <s.StateText size={size} fieldState={fieldState}>
        {bottomText()}
      </s.StateText>
    </>
  );
};

export default Textfield;
