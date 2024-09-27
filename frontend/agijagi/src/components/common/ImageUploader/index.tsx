import { useState } from 'react';

import * as s from './style';

import Preview from './Preview';
import Upload from './Uplaod';
import useDialog from '../../../hooks/useDialog';

interface ImageUploaderProps {
  maxFileCount?: number;
}

const ImageUploader = ({ maxFileCount = 1 }: ImageUploaderProps) => {
  const { alert } = useDialog();

  const [fileList, setFileList] = useState<File[]>([]);

  const handleFileChange = (files: FileList) => {
    const file: File[] = [];

    if (fileList.length + files.length > maxFileCount) {
      alert(`최대 ${maxFileCount}개 이미지만 업로드 할 수 있어요.`);
      return;
    }

    for (let i = 0; i < files.length; i++) {
      file.push(files[i]);
    }

    setFileList((fileList) => [...fileList, ...file]);
  };

  const handleRemove = (file: File) => {
    setFileList(fileList.filter((item) => item !== file));
  };

  return (
    <s.Container>
      <s.Preview>
        {fileList.map((file, index) => (
          <Preview
            key={index}
            src={URL.createObjectURL(file)}
            onRemove={() => handleRemove(file)}
          />
        ))}
      </s.Preview>
      <Upload onChange={handleFileChange} />
    </s.Container>
  );
};

export default ImageUploader;
