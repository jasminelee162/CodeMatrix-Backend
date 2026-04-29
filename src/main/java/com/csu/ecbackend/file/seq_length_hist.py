import pandas as pd
import matplotlib.pyplot as plt

# 1. 读取 CSV 文件
file_path = "gene_cold_test0.csv"
df = pd.read_csv(file_path)

# 2. 检查是否存在 'seq' 列
if 'seq' not in df.columns:
    raise ValueError("CSV 文件中没有 'seq' 列，请检查列名是否正确。")

# 3. 计算 seq 列中每个字符串的长度
seq_lengths = df['seq'].astype(str).apply(len)

# 4. 输出一些基本统计信息
print("seq 字符串长度统计：")
print(seq_lengths.describe())

# 5. 绘制直方图
plt.figure(figsize=(10, 6))
plt.hist(seq_lengths, bins=30, color='skyblue', edgecolor='black')
plt.title('Distribution of seq String Lengths')
plt.xlabel('Length of seq')
plt.ylabel('Frequency')
plt.grid(axis='y', alpha=0.75)
plt.show()