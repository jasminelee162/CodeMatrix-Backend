import os
from pathlib import Path

import pandas as pd
from sklearn.model_selection import KFold, train_test_split


def check_columns(df: pd.DataFrame, required_cols):
    missing = [col for col in required_cols if col not in df.columns]
    if missing:
        raise ValueError(f"GDI.csv 缺少必要列: {missing}")


def make_group_column(df: pd.DataFrame, mode: str) -> pd.Series:
    """
    根据 cold start 模式生成分组键：
    - gene: 按 gene_name 分组
    - drug: 按 drug_name 分组
    - asso: 按 (gene_name, drug_name) 分组
    """
    if mode == "gene":
        return df["gene_name"].astype(str)
    elif mode == "drug":
        return df["drug_name"].astype(str)
    elif mode == "asso":
        return df["gene_name"].astype(str) + "||" + df["drug_name"].astype(str)
    else:
        raise ValueError(f"不支持的 mode: {mode}")


def generate_cold_start_folds_with_independent(
    df: pd.DataFrame,
    mode: str,
    output_dir: str,
    n_splits: int = 5,
    independent_ratio: float = 0.1,
    random_state: int = 42
):
    """
    先按 group 划分：
    1. 抽取 10% group 作为独立验证集 independent
    2. 剩余 90% group 再进行 5-fold 交叉验证

    输出：
    - {mode}_cold_independent.csv
    - {mode}_cold_train0.csv ~ {mode}_cold_train4.csv
    - {mode}_cold_test0.csv ~ {mode}_cold_test4.csv
    """
    group_col = make_group_column(df, mode)
    unique_groups = group_col.drop_duplicates().tolist()

    if len(unique_groups) < 2:
        raise ValueError(f"{mode} cold start 的唯一分组数不足，无法划分独立验证集。")

    # 先划分 independent group 和 remaining group
    remain_groups, independent_groups = train_test_split(
        unique_groups,
        test_size=independent_ratio,
        random_state=random_state,
        shuffle=True
    )

    if len(remain_groups) < n_splits:
        raise ValueError(
            f"{mode} cold start 在划分 10% 独立集后，剩余分组数只有 {len(remain_groups)}，"
            f"少于 {n_splits}，无法做 {n_splits}-fold。"
        )

    output_dir = Path(output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    # 保存 independent
    independent_mask = group_col.isin(independent_groups)
    independent_df = df[independent_mask].reset_index(drop=True)
    independent_file = output_dir / f"{mode}_cold_independent.csv"
    independent_df.to_csv(independent_file, index=False, encoding="utf-8-sig")

    print(
        f"[{mode}] independent: "
        f"samples={len(independent_df)}, groups={len(independent_groups)}"
    )

    # 对剩余 90% group 做 5-fold
    kf = KFold(n_splits=n_splits, shuffle=True, random_state=random_state)

    remain_groups = list(remain_groups)

    for fold_idx, (train_group_idx, test_group_idx) in enumerate(kf.split(remain_groups)):
        train_groups = {remain_groups[i] for i in train_group_idx}
        test_groups = {remain_groups[i] for i in test_group_idx}

        train_mask = group_col.isin(train_groups)
        test_mask = group_col.isin(test_groups)

        train_df = df[train_mask].reset_index(drop=True)
        test_df = df[test_mask].reset_index(drop=True)

        train_file = output_dir / f"{mode}_cold_train{fold_idx}.csv"
        test_file = output_dir / f"{mode}_cold_test{fold_idx}.csv"

        train_df.to_csv(train_file, index=False, encoding="utf-8-sig")
        test_df.to_csv(test_file, index=False, encoding="utf-8-sig")

        print(
            f"[{mode}] fold {fold_idx}: "
            f"train={len(train_df)} 条, test={len(test_df)} 条, "
            f"train_groups={len(train_groups)}, test_groups={len(test_groups)}"
        )


def main():
    input_file = "GDI.csv"
    output_root = "cold_start_5cv"

    required_cols = ["gene_name", "drug_name", "seq", "smiles", "label"]

    df = pd.read_csv(input_file)
    check_columns(df, required_cols)

    # 去掉完全重复行（可选）
    df = df.drop_duplicates().reset_index(drop=True)

    print(f"总样本数: {len(df)}")
    print(f"唯一基因数: {df['gene_name'].nunique()}")
    print(f"唯一药物数: {df['drug_name'].nunique()}")
    print(f"唯一关联对数: {df[['gene_name', 'drug_name']].drop_duplicates().shape[0]}")
    print("-" * 60)

    # 1) gene cold start
    generate_cold_start_folds_with_independent(
        df=df,
        mode="gene",
        output_dir=os.path.join(output_root, "gene_cold"),
        n_splits=5,
        independent_ratio=0.1,
        random_state=42
    )

    print("-" * 60)

    # 2) drug cold start
    generate_cold_start_folds_with_independent(
        df=df,
        mode="drug",
        output_dir=os.path.join(output_root, "drug_cold"),
        n_splits=5,
        independent_ratio=0.1,
        random_state=42
    )

    print("-" * 60)

    # 3) association cold start
    generate_cold_start_folds_with_independent(
        df=df,
        mode="asso",
        output_dir=os.path.join(output_root, "asso_cold"),
        n_splits=5,
        independent_ratio=0.1,
        random_state=42
    )

    print("-" * 60)
    print("所有 cold start 数据集已生成完成。")


if __name__ == "__main__":
    main()